//
//  CoinTopAppBar.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI

private let AppBarHeight: Double = 56

struct CoinTopAppBar<Title: View, NavIcon: View, Actions: View>: View {
    
    @EnvironmentObject private var theme: CoinTheme
    @Environment(\.safeAreaInsets) private var safeAreaInsets: (top: CGFloat, bottom: CGFloat)
    
    var title: () -> Title
    var navigationIcon: () -> NavIcon
    var actions: () -> Actions
    var appBarHeight: Double
    
    init(
        @ViewBuilder title: @escaping () -> Title,
        @ViewBuilder navigationIcon: @escaping () -> NavIcon = { EmptyView() },
        @ViewBuilder actions: @escaping () -> Actions = { EmptyView() },
        appBarHeight: Double = AppBarHeight
    ) {
        self.title = title
        self.navigationIcon = navigationIcon
        self.actions = actions
        self.appBarHeight = appBarHeight
    }
    
    var body: some View {
        HStack {
            navigationIcon()
            
            title()
                .padding(.leading, 16)
                .padding(.trailing, 16)
            Spacer()
            actions()
                .padding(.trailing, 16)
        }
        .frame(height: appBarHeight)
        .frame(maxWidth: .infinity)
        .background(theme.colors.backgroundSurface)
        .safeAreaInset(edge: .top) {
            Spacer().frame(height: safeAreaInsets.top)
        }
        .shadow(radius: 1)
    }
}

struct CoinTopAppBar_Previews: PreviewProvider {
    static var previews: some View {
        CoinTopAppBar(
            title: {
                Text("Title")
            }
        )
        .environmentObject(CoinTheme.light)
    }
}
