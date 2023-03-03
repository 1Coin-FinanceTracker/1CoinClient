//
//  CoinTabView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 25.02.2023.
//

import SwiftUI
import OneCoinShared

struct CoinTabView: View {
    
    @Binding var selectedTab: Tab
    
    var body: some View {
        HStack {
            Spacer()
            Group {
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
                Spacer()
                CoinTab(
                    text: "",
                    url: MR.files().ic_transactions_inactive.url,
                    isActive: selectedTab == .add
                ) { selectedTab = .add }
                Spacer()
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
            }
            Spacer()
        }
    }
}

private struct CoinTab: View {
    
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
                    .tintColor(isActive ? .blue : .primary)
                    .frameSvg(width: 25, height: 25)
                Text(text)
                    .font(.caption2)
                    .foregroundColor(isActive ? .blue : .primary)
            }
        }
    }
}

//struct CoinTabView_Previews: PreviewProvider {
//    static var previews: some View {
//        //CoinTabView(selectedTab: Tab.home)
//    }
//}
