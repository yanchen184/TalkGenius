package com.talkgenius.keyboard

import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.talkgenius.data.model.ToneStyle
import com.talkgenius.ui.keyboard.KeyboardView
import com.talkgenius.ui.theme.TalkGeniusTheme
import com.talkgenius.ui.viewmodel.KeyboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Custom InputMethodService for TalkGenius AI keyboard.
 * Provides AI-powered reply suggestions based on received messages.
 */
@AndroidEntryPoint
class TalkGeniusKeyboardService : InputMethodLifecycleService() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: KeyboardViewModel
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var keyboardView: ComposeView? = null
    private var currentToneStyle: ToneStyle = ToneStyle.Humorous

    // UI State
    private var inputText by mutableStateOf("")
    private var isLoading by mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        viewModel = ViewModelProvider(this, viewModelFactory)[KeyboardViewModel::class.java]

        // Observe AI reply state
        lifecycleScope.launch {
            viewModel.aiReplyState.collectLatest { state ->
                isLoading = state.isLoading

                when {
                    state.reply != null -> {
                        // Insert AI-generated reply
                        insertGeneratedReply(state.reply)
                        showToast("AI 回覆已生成!")

                        // Clear input after sending
                        inputText = ""
                        viewModel.resetState()
                    }
                    state.error != null -> {
                        showToast("錯誤: ${state.error}")
                        isLoading = false
                    }
                }
            }
        }
    }

    override fun onCreateInputView(): View {
        // Create Compose view for keyboard UI
        keyboardView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@TalkGeniusKeyboardService)
            setViewTreeViewModelStoreOwner(this@TalkGeniusKeyboardService)
            setViewTreeSavedStateRegistryOwner(this@TalkGeniusKeyboardService)

            setContent {
                TalkGeniusTheme {
                    KeyboardView(
                        onPasteClick = { handlePaste() },
                        onToneSelected = { tone -> setToneStyle(tone) },
                        onClearClick = { inputText = "" },
                        onSendClick = { handleSend() },
                        inputText = inputText,
                        onInputChange = { inputText = it },
                        isLoading = isLoading
                    )
                }
            }
        }

        return keyboardView!!
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        // Keyboard view started - ready for input
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        // Keyboard view finished
    }

    /**
     * Handle paste button click - reads from clipboard
     */
    private fun handlePaste() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip

        if (clipData != null && clipData.itemCount > 0) {
            val text = clipData.getItemAt(0).text?.toString()
            if (!text.isNullOrBlank()) {
                inputText = text
                showToast("已粘貼文本")
            } else {
                showToast("剪貼簿為空")
            }
        } else {
            showToast("剪貼簿為空")
        }
    }

    /**
     * Handle send button click - generates AI reply
     */
    private fun handleSend() {
        if (inputText.isBlank()) {
            showToast("請先輸入或粘貼訊息")
            return
        }

        serviceScope.launch {
            isLoading = true
            try {
                // Build conversation context from history
                val context = viewModel.buildConversationContext()

                // Generate reply using AI
                viewModel.generateReply(
                    receivedMessage = inputText,
                    toneStyle = currentToneStyle,
                    conversationContext = context
                )
            } catch (e: Exception) {
                showToast("生成回覆失敗: ${e.message}")
                isLoading = false
            }
        }
    }

    /**
     * Generate AI reply for the selected text.
     * This will be called from the keyboard UI when user requests AI assistance.
     */
    fun generateAIReply(selectedText: String) {
        inputText = selectedText
        handleSend()
    }

    /**
     * Insert the AI-generated reply into the current input connection.
     */
    private fun insertGeneratedReply(reply: String) {
        val ic = currentInputConnection ?: return
        ic.commitText(reply, 1)
    }

    /**
     * Set the current tone style for AI replies.
     */
    fun setToneStyle(toneStyle: ToneStyle) {
        currentToneStyle = toneStyle
        showToast("Tone style: ${toneStyle.name}")
    }

    /**
     * Get selected text from the input field.
     */
    fun getSelectedText(): String? {
        val ic = currentInputConnection ?: return null
        val selectedText = ic.getSelectedText(0)
        return selectedText?.toString()
    }

    /**
     * Show a toast message.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        keyboardView = null
    }
}
