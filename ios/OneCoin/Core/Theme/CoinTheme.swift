//
//  CoinTheme.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI

class CoinTheme: ObservableObject {
    let colors: CoinColors
    let typography: Typography
    
    init(colors: CoinColors, typography: Typography) {
        self.colors = colors
        self.typography = typography
    }
}

extension CoinTheme {
    
    static let dark = CoinTheme(
        colors: CoinColors.dark,
        typography: Typography.main
    )
    
    static let light = CoinTheme(
        colors: CoinColors.light,
        typography: Typography.main
    )
}
