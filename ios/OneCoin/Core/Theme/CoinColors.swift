//
//  Colors.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI
import OneCoinShared

struct CoinColors {
    let primary: SwiftUI.Color
    let primaryVariant: SwiftUI.Color
    let secondary: SwiftUI.Color
    let background: SwiftUI.Color
    let backgroundSurface: SwiftUI.Color
    let secondaryBackground: SwiftUI.Color
    let dividers: SwiftUI.Color
    let content: SwiftUI.Color
    let accentGreen: SwiftUI.Color
    let accentRed: SwiftUI.Color
    let white: SwiftUI.Color
    
    init(
        primary: SwiftUI.Color,
        primaryVariant: SwiftUI.Color,
        secondary: SwiftUI.Color,
        background: SwiftUI.Color,
        backgroundSurface: SwiftUI.Color,
        secondaryBackground: SwiftUI.Color,
        dividers: SwiftUI.Color,
        content: SwiftUI.Color,
        accentGreen: SwiftUI.Color,
        accentRed: SwiftUI.Color,
        white: SwiftUI.Color
    ) {
        self.primary = primary
        self.primaryVariant = primaryVariant
        self.secondary = secondary
        self.background = background
        self.backgroundSurface = backgroundSurface
        self.secondaryBackground = secondaryBackground
        self.dividers = dividers
        self.content = content
        self.accentGreen = accentGreen
        self.accentRed = accentRed
        self.white = white
    }
}

extension CoinColors {
    
    static let darkColorPalette = ColorPalette.shared.provideColorPalette(isDarkMode: true)
    static let lightColorPalette = ColorPalette.shared.provideColorPalette(isDarkMode: false)
    
    static let dark = CoinColors(
        primary: darkColorPalette.primary.toSwiftUIColor(),
        primaryVariant: darkColorPalette.primaryVariant.toSwiftUIColor(),
        secondary: darkColorPalette.secondary.toSwiftUIColor(),
        background: darkColorPalette.background.toSwiftUIColor(),
        backgroundSurface: darkColorPalette.backgroundSurface.toSwiftUIColor(),
        secondaryBackground: darkColorPalette.secondaryBackground.toSwiftUIColor(),
        dividers: darkColorPalette.dividers.toSwiftUIColor(),
        content: darkColorPalette.content.toSwiftUIColor(),
        accentGreen: darkColorPalette.accentGreen.toSwiftUIColor(),
        accentRed: darkColorPalette.accentRed.toSwiftUIColor(),
        white: darkColorPalette.white.toSwiftUIColor()
    )
    
    static let light = CoinColors(
        primary: lightColorPalette.primary.toSwiftUIColor(),
        primaryVariant: lightColorPalette.primaryVariant.toSwiftUIColor(),
        secondary: lightColorPalette.secondary.toSwiftUIColor(),
        background: lightColorPalette.background.toSwiftUIColor(),
        backgroundSurface: lightColorPalette.backgroundSurface.toSwiftUIColor(),
        secondaryBackground: lightColorPalette.secondaryBackground.toSwiftUIColor(),
        dividers: lightColorPalette.dividers.toSwiftUIColor(),
        content: lightColorPalette.content.toSwiftUIColor(),
        accentGreen: lightColorPalette.accentGreen.toSwiftUIColor(),
        accentRed: lightColorPalette.accentRed.toSwiftUIColor(),
        white: lightColorPalette.white.toSwiftUIColor()
    )
}

extension OneCoinShared.Color {
    func toSwiftUIColor() -> SwiftUI.Color {
        return Color(self.toUIColor())
    }
}
