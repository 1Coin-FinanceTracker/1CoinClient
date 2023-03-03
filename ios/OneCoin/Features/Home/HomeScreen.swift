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
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("HomeScreen")
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
