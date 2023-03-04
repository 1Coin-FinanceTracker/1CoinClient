//
//  TransactionsAppBar.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI
import OneCoinShared

struct TransactionsAppBar: View {
    var body: some View {
        CoinTopAppBar(
            title: {
                Text(MR.strings().transactions_title_normal.desc().localized())
                    .fontH4Style()
            }
        )
    }
}

struct TransactionsAppBar_Previews: PreviewProvider {
    static var previews: some View {
        TransactionsAppBar()
    }
}
