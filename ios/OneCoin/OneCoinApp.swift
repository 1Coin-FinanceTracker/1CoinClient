//
//  OneCoinApp.swift
//  OneCoin
//
//  Created by Виталий Перятин on 07.02.2023.
//

import SwiftUI
import OneCoinShared

@main
struct OneCoinApp: App {
    
    private let diManager = DiManager.shared
    
    init() {
        diManager.configure()
        
        initApp()
    }
    
    private func initApp() {
        let appInitializer = diManager.core.appInitializer()
        appInitializer.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            TabsNavigationScreen()
        }
    }
}
