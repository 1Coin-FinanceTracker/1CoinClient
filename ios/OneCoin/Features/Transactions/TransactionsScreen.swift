//
//  TransactionsScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI

struct TransactionsScreen: View {
    var body: some View {
        VStack {
            TransactionsAppBar()
            Spacer()
            Text("TransactionsScreen")
            Spacer()
        }
    }
}

struct TransactionsScreen_Previews: PreviewProvider {
    static var previews: some View {
        TransactionsScreen()
    }
}
