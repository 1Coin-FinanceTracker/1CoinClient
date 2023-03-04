//
//  SVGImageView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 25.02.2023.
//

import Foundation
import UIKit
import SwiftUI
import SVGKit

struct SVGImageView: UIViewRepresentable {
    
    var url: URL
    var size: CGSize = CGSize(width: 50, height: 50)
    var tint: Color = .black
    
    init(url: URL) {
        self.url = url
    }
    
    // MARK:- Public Method(s)
    func updateUIView(_ uiView: SVGKFastImageView, context: Context) {
        uiView.contentMode = .scaleAspectFit
        uiView.image.size = size
        fillColorForSubLayer(layer: uiView.image.caLayerTree, color: tint, opacity: 1)
    }
    
    func makeUIView(context: Context) -> SVGKFastImageView {
        let svgImage = SVGKImage(contentsOf: url)
        return SVGKFastImageView(svgkImage: svgImage ?? SVGKImage())
    }
}

struct SVGImageView_Previews: PreviewProvider {
  static var previews: some View {
    SVGImageView(
        url:URL(string:"https://dev.w3.org/SVG/tools/svgweb/samples/svg-files/atom.svg")!
    )
    .frameSvg(width: 50, height: 50)
  }
}

extension SVGImageView {
    
    // MARK:- Public Method(s)
    @inlinable func frameSvg(
        width: CGFloat,
        height: CGFloat
    ) -> some View {
        var view = self
        view.size = CGSize(width: width, height: height)
        return view
            .frame(width: width, height: height)
    }
    
    @inlinable func tintColor(
        _ color: Color
    ) -> SVGImageView {
        var view = self
        view.tint = color
        return view
    }
    
    // MARK:- Private Method(s)
    private func fillColorForSubLayer(layer: CALayer, color: Color, opacity: Float) {
        if layer is CAShapeLayer {
            let shapeLayer = layer as! CAShapeLayer
            shapeLayer.fillColor = UIColor(color).cgColor
            shapeLayer.opacity = opacity
        }

        if let sublayers = layer.sublayers, sublayers.count > 1 {
            for index in 1..<sublayers.count {
                fillColorForSubLayer(layer: sublayers[index], color: color, opacity: opacity)
            }
        }
    }
}


struct ImageView_Previews: PreviewProvider {
  static var previews: some View {
    AsyncImage(url: URL(string: "https://dev.w3.org/SVG/tools/svgweb/samples/svg-files/atom.svg"))
          .frame(width: 50, height: 50)
  }
}
