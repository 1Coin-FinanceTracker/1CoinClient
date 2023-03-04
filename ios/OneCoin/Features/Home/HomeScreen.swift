//
//  ContentView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 07.02.2023.
//

import SwiftUI
import OneCoinShared

struct HomeScreen: View {

    var body: some View {
        VStack {
            HomeTopBar(
                //totalBalance: OneCoinShared.Amount(currency: OneCoinShared.Currency(code: "", symbol: ""), amountValue: 1),
                onSettingsClick: {}
            )
            ScrollView(.vertical, showsIndicators: false) {
                VStack {
                    ForEach(0..<10) {
                                Text("Item \($0)")
                                    .foregroundColor(.white)
                                    .font(.largeTitle)
                                    .frame(width: 200, height: 200)
                                    .background(.red)
                            }
                }
            }
            Spacer()
//            CoinTopAppBar(
//                title: {
//                    Text("HomeScreen")
//                },
//                actions: {
//                    Image(systemName: "globe")
//                        .imageScale(.large)
//                        .foregroundColor(.accentColor)
//                },
//                appBarHeight: 56
//            )
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
