//
//  TabsNavigationScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI
import OneCoinShared

enum Tab {
    case home
    case transactions
    case add
    case accounts
    case analytics
    
}

struct TabsNavigationScreen: View {
    
    @State private var selectedTab: Tab = .home
    
    var body: some View {
        VStack {
            ZStack {
                
                Color.red
                
                switch selectedTab {
                case .home:
                    HomeScreen()
                case .transactions:
                    TransactionsScreen()
                case .add:
                    AddTransactionScreen()
                case .accounts:
                    AccountsScreen()
                case .analytics:
                    AnalyticsScreen()
                }
            }
            CoinTabView(selectedTab: $selectedTab)
        }
    }
}

struct TabsNavigationScreen_Previews: PreviewProvider {
    static var previews: some View {
        TabsNavigationScreen()
    }
}
