//
//  AppDelegate.swift
//  TalkGenius
//
//  Application delegate.
//

import UIKit
import CoreData

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Initialize window
        window = UIWindow(frame: UIScreen.main.bounds)

        // Check authentication status
        if TokenManager.shared.isAuthenticated() {
            // User is logged in - show dashboard
            let dashboardVC = DashboardViewController()
            let navController = UINavigationController(rootViewController: dashboardVC)
            window?.rootViewController = navController
        } else {
            // User not logged in - show auth
            let authVC = AuthViewController()
            let navController = UINavigationController(rootViewController: authVC)
            window?.rootViewController = navController
        }

        window?.makeKeyAndVisible()

        return true
    }

    func applicationWillTerminate(_ application: UIApplication) {
        CoreDataManager.shared.saveContext()
    }
}
