//
//  Typography.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI
import OneCoinShared

struct Typography {
    
    var h1: Font
    var h2: Font
    var h3: Font
    var h4: Font
    var h5: Font
    var body1: Font
    var body1_medium: Font
    var subtitle1: Font
    var subtitle2: Font
    var subtitle2_medium: Font
    var subtitle3: Font
    var subtitle4: Font
}

extension Typography {
    static let main = Typography(
        h1: Font.rubikMedium(withSize: 42),
        h2: Font.rubikMedium(withSize: 32),
        h3: Font.rubikRegular(withSize: 22),
        h4: Font.rubikMedium(withSize: 20),
        h5: Font.rubikMedium(withSize: 18),
        body1: Font.rubikRegular(withSize: 14),
        body1_medium: Font.rubikMedium(withSize: 14),
        subtitle1: Font.rubikMedium(withSize: 13),
        subtitle2: Font.rubikRegular(withSize: 12),
        subtitle2_medium: Font.rubikMedium(withSize: 12),
        subtitle3: Font.rubikMedium(withSize: 11),
        subtitle4: Font.rubikMedium(withSize: 10)
    )
}

struct DefaultTextStyle :  ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .foregroundColor(fontColor ?? theme.colors.content)
            .multilineTextAlignment(.leading)
    }
}


struct H1Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.h1)
    }
}

struct H2Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.h2)
    }
}

struct H3Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.h3)
    }
}

struct H4Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.h4)
    }
}

struct H5Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.h5)
    }
}

struct Body1Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.body1)
    }
}

struct Body1MediumStyle : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.body1_medium)
    }
}

struct Subtitle1Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.subtitle1)
    }
}

struct Subtitle2Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.subtitle2)
    }
}

struct Subtitle2MediumStyle : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.subtitle2_medium)
    }
}

struct Subtitle3Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.subtitle3)
    }
}

struct Subtitle4Style : ViewModifier {
    @EnvironmentObject var theme: CoinTheme
    var fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(theme.typography.subtitle4)
    }
}

extension View {
    
    func fontH1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H1Style(fontColor: color))
    }
    
    func fontH2Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H2Style(fontColor: color))
    }
    
    func fontH3Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H3Style(fontColor: color))
    }
    
    func fontH4Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H4Style(fontColor: color))
    }
    
    func fontH5Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H5Style(fontColor: color))
    }
    
    func fontBody1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Body1Style(fontColor: color))
    }
    
    func fontBody1MediumStyle(color: SwiftUI.Color? = nil) -> some View {
        modifier(Body1MediumStyle(fontColor: color))
    }
    
    func fontSubtitle1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle1Style(fontColor: color))
    }
    
    func fontSubtitle2Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle2Style(fontColor: color))
    }
    
    func fontSubtitle2MediumStyle(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle2MediumStyle(fontColor: color))
    }
    
    func fontSubtitle3Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle3Style(fontColor: color))
    }
    
    func fontSubtitle4Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle4Style(fontColor: color))
    }
}

extension Font {
    
    static func rubikMedium(withSize: Double) -> Font {
        MR.fontsRubik().medium.toSwiftUIFont(withSize: withSize)
    }
    
    static func rubikRegular(withSize: Double) -> Font {
        MR.fontsRubik().regular.toSwiftUIFont(withSize: withSize)
    }
}

extension FontResource {
    func toSwiftUIFont(withSize: Double) -> Font {
        return Font(uiFont(withSize: withSize))
    }
}
