//
//  ThemeManager.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI

class ThemeManager: ObservableObject {
    @Published var current: CoinTheme = .light
}
