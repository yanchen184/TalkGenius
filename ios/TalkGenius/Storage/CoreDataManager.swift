//
//  CoreDataManager.swift
//  TalkGenius
//
//  Core Data stack manager.
//

import Foundation
import CoreData

class CoreDataManager {

    static let shared = CoreDataManager()

    private init() {}

    // MARK: - Core Data Stack

    lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "TalkGenius")
        container.loadPersistentStores { description, error in
            if let error = error {
                fatalError("Unable to load persistent stores: \(error)")
            }
        }
        return container
    }()

    var context: NSManagedObjectContext {
        return persistentContainer.viewContext
    }

    // MARK: - Save Context

    func saveContext() {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }

    // MARK: - User Operations

    func saveUser(userId: String, email: String, displayName: String?, isPremium: Bool) {
        let fetchRequest: NSFetchRequest<UserEntity> = UserEntity.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "userId == %@", userId)

        do {
            let results = try context.fetch(fetchRequest)
            let user: UserEntity

            if let existingUser = results.first {
                user = existingUser
            } else {
                user = UserEntity(context: context)
                user.userId = userId
                user.createdAt = Date()
            }

            user.email = email
            user.displayName = displayName
            user.isPremium = isPremium

            saveContext()
        } catch {
            print("Error saving user: \(error)")
        }
    }

    func getUser(userId: String) -> UserEntity? {
        let fetchRequest: NSFetchRequest<UserEntity> = UserEntity.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "userId == %@", userId)

        do {
            let results = try context.fetch(fetchRequest)
            return results.first
        } catch {
            print("Error fetching user: \(error)")
            return nil
        }
    }

    // MARK: - Conversation Operations

    func saveConversation(
        conversationId: String,
        userId: String,
        receivedMessage: String,
        generatedReply: String,
        toneStyle: String,
        contextSnapshot: String?
    ) {
        let conversation = ConversationEntity(context: context)
        conversation.conversationId = conversationId
        conversation.userId = userId
        conversation.receivedMessage = receivedMessage
        conversation.generatedReply = generatedReply
        conversation.toneStyle = toneStyle
        conversation.contextSnapshot = contextSnapshot
        conversation.createdAt = Date()

        saveContext()
    }

    func getConversations(userId: String, limit: Int = 10) -> [ConversationEntity] {
        let fetchRequest: NSFetchRequest<ConversationEntity> = ConversationEntity.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "userId == %@", userId)
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "createdAt", ascending: false)]
        fetchRequest.fetchLimit = limit

        do {
            return try context.fetch(fetchRequest)
        } catch {
            print("Error fetching conversations: \(error)")
            return []
        }
    }

    func deleteAllConversations(userId: String) {
        let fetchRequest: NSFetchRequest<NSFetchRequestResult> = ConversationEntity.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "userId == %@", userId)

        let deleteRequest = NSBatchDeleteRequest(fetchRequest: fetchRequest)

        do {
            try context.execute(deleteRequest)
            saveContext()
        } catch {
            print("Error deleting conversations: \(error)")
        }
    }
}

// MARK: - Core Data Entities (Manual Implementation)

@objc(UserEntity)
public class UserEntity: NSManagedObject {
    @NSManaged public var userId: String
    @NSManaged public var email: String
    @NSManaged public var displayName: String?
    @NSManaged public var isPremium: Bool
    @NSManaged public var createdAt: Date

    @nonobjc public class func fetchRequest() -> NSFetchRequest<UserEntity> {
        return NSFetchRequest<UserEntity>(entityName: "UserEntity")
    }
}

@objc(ConversationEntity)
public class ConversationEntity: NSManagedObject {
    @NSManaged public var conversationId: String
    @NSManaged public var userId: String
    @NSManaged public var receivedMessage: String
    @NSManaged public var generatedReply: String
    @NSManaged public var toneStyle: String
    @NSManaged public var contextSnapshot: String?
    @NSManaged public var createdAt: Date

    @nonobjc public class func fetchRequest() -> NSFetchRequest<ConversationEntity> {
        return NSFetchRequest<ConversationEntity>(entityName: "ConversationEntity")
    }
}
