//
//  AppScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI

struct AppScreen: View {
    
    @StateObject var themeManeger = ThemeManager()
    @Environment(\.colorScheme) var colorScheme
    
    @State private var safeAreaInsets: (top: CGFloat, bottom: CGFloat) = (0, 0)
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                TabsNavigationScreen()
                    .ignoresSafeArea()
                    .environmentObject(themeManeger)
                    .environmentObject(themeManeger.current)
                    .environment(\.safeAreaInsets, safeAreaInsets)
                    .onAppear {
                        applyActualTheme(newColorScheme: colorScheme)
                    }
                    .onAppear {
                        safeAreaInsets = (proxy.safeAreaInsets.top, proxy.safeAreaInsets.bottom)
                    }
                    .onChange(of: colorScheme) { newColorScheme in
                        applyActualTheme(newColorScheme: newColorScheme)
                    }
            }
        }
    }
    
    private func applyActualTheme(newColorScheme: ColorScheme) {
        if newColorScheme == .light {
            themeManeger.current = .light
        } else {
            themeManeger.current = .dark
        }
    }
}

struct AppScreen_Previews: PreviewProvider {
    static var previews: some View {
        AppScreen()
    }
}
