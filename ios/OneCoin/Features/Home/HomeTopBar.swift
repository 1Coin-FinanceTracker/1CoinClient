//
//  HomeTopBar.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI
import OneCoinShared

struct HomeTopBar: View {
    
    @EnvironmentObject private var theme: CoinTheme
    
    //let totalBalance: Amount
    let onSettingsClick: () -> ()
    
    var body: some View {
        CoinTopAppBar(
            title: {
                VStack(alignment: .leading, spacing: 2) {
                    Text("0")
                        .fontH4Style()
                    Text(MR.strings().home_topbar_text.desc().localized())
                        .fontSubtitle2Style(color: theme.colors.secondary)
                }
            },
            actions: {
                Button {
                    onSettingsClick()
                } label: {
                    SVGImageView(url: MR.files().ic_settings.url)
                        .tintColor(theme.colors.content)
                        .frameSvg(width: 25, height: 25)
                }
            }
        )
    }
}

struct HomeTopBar_Previews: PreviewProvider {
    static var previews: some View {
        HomeTopBar(
            onSettingsClick: {
                print("")
            }
        )
    }
}
