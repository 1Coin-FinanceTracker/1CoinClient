//
//  CoinTabView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 25.02.2023.
//

import SwiftUI
import OneCoinShared

struct CoinTabView: View {
    
    @EnvironmentObject var theme: CoinTheme
    @Binding var selectedTab: Tab
    
    var body: some View {
        HStack {
            Spacer()
            CoinTab(
                text: MR.strings().tab_home.desc().localized(),
                url: MR.files().ic_home_inactive.url,
                isActive: selectedTab == .home
            ) { selectedTab = .home }
            Spacer()
            CoinTab(
                text: MR.strings().tab_transactions.desc().localized(),
                url: MR.files().ic_transactions_inactive.url,
                isActive: selectedTab == .transactions
            ) { selectedTab = .transactions }

            Button {
                selectedTab = .add
            } label: {
                ZStack {
                    Circle()
                        .foregroundColor(theme.colors.primary)
                        .frame(width: 56, height: 56)
                        .shadow(radius: 4)

                    SVGImageView(url: MR.files().ic_plus.url)
                        .tintColor(theme.colors.white)
                        .frameSvg(width: 30, height: 30)
                }
                .offset(y: -32)
            }

            CoinTab(
                text: MR.strings().tab_accounts.desc().localized(),
                url: MR.files().ic_wallet_inactive.url,
                isActive: selectedTab == .accounts
            ) { selectedTab = .accounts }
            Spacer()
            CoinTab(
                text: MR.strings().tab_analytics.desc().localized(),
                url: MR.files().ic_analytics_inactive.url,
                isActive: selectedTab == .analytics
            ) { selectedTab = .analytics }
            Spacer()
        }
        .background(theme.colors.backgroundSurface)
        .shadow(radius: 1)
    }
}

private struct CoinTab: View {
    
    @EnvironmentObject var theme: CoinTheme
    
    let text: String
    let url: URL
    var isActive: Bool
    let action: () -> ()
    
    init(text: String, url: URL, isActive: Bool, action: @escaping () -> Void) {
        self.text = text
        self.url = url
        self.isActive = isActive
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            VStack {
                SVGImageView(url: url)
                    .tintColor(isActive ? theme.colors.primary : theme.colors.secondary)
                    .frameSvg(width: 25, height: 25)
                Text(text)
                    .fontSubtitle4Style(color: isActive ? theme.colors.primary : theme.colors.secondary)
            }
        }
    }
}

//struct CoinTabView_Previews: PreviewProvider {
//    static var previews: some View {
//        //CoinTabView(selectedTab: Tab.home)
//    }
//}
