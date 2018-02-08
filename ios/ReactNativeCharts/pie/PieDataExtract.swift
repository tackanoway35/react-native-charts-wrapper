//  Created by xudong wu on 02/03/2017.
//  Copyright © 2017 wuxudong. All rights reserved.
//

import Foundation

import SwiftyJSON
import Charts
import UIKit

extension UIColor {
  convenience init?(hexString: String) {
    var chars = Array(hexString.hasPrefix("#") ? hexString.characters.dropFirst() : hexString.characters)  // Swift 4 just use `var chars = Array(hexString.hasPrefix("#") ? hexString.dropFirst() : hexString[...])`
    let red, green, blue, alpha: CGFloat
    switch chars.count {
    case 3:
      chars = chars.flatMap { [$0, $0] }
      fallthrough
    case 6:
      chars = ["F","F"] + chars
      fallthrough
    case 8:
      alpha = CGFloat(strtoul(String(chars[0...1]), nil, 16)) / 255
      red   = CGFloat(strtoul(String(chars[2...3]), nil, 16)) / 255
      green = CGFloat(strtoul(String(chars[4...5]), nil, 16)) / 255
      blue  = CGFloat(strtoul(String(chars[6...7]), nil, 16)) / 255
    default:
      return nil
    }
    self.init(red: red, green: green, blue:  blue, alpha: alpha)
  }
}

class PieDataExtract : DataExtract {
    override func createData() -> ChartData {
        return PieChartData();
    }
    
    override func createDataSet(_ entries: [ChartDataEntry]?, label: String?) -> IChartDataSet {
        return PieChartDataSet(values: entries, label: label)
    }
    
    override func dataSetConfig(_ dataSet: IChartDataSet, config: JSON) {
        let pieDataSet = dataSet as! PieChartDataSet;
      
        if (config["xValuePosition"].bool != nil) {
          var isDrawOutSide : Bool
          isDrawOutSide = (config["xValuePosition"].bool)!
          if (isDrawOutSide == true) {
            pieDataSet.xValuePosition = .outsideSlice
          } else {
            pieDataSet.xValuePosition = .insideSlice
          }
        }
      
        if config["valueLineWidth"].float != nil {
            let valueConfig = CGFloat(config["valueLineWidth"].float!)
            pieDataSet.valueLineWidth = valueConfig
        }
      
        if config["valueLinePart1Length"].float != nil {
            let valueConfig = CGFloat(config["valueLinePart1Length"].float!)
            pieDataSet.valueLinePart1Length = valueConfig
        }
      
        if config["valueLinePart2Length"].float != nil {
            let valueConfig = CGFloat(config["valueLinePart2Length"].float!)
            pieDataSet.valueLinePart1Length = valueConfig
        }
      
        if config["valueLineColor"].string != nil {
            let valueConfig = config["valueLineColor"].string
            pieDataSet.valueLineColor = UIColor(hexString: valueConfig!)
        }
      
        ChartDataSetConfigUtils.commonConfig(pieDataSet, config: config);
        
        // PieDataSet only config
        if config["sliceSpace"].number != nil {
            pieDataSet.sliceSpace = CGFloat(config["sliceSpace"].numberValue)
        }
        
        if config["selectionShift"].number != nil {
            pieDataSet.selectionShift = CGFloat(config["selectionShift"].numberValue)
        }
  }
    
    override func createEntry(_ values: [JSON], index: Int) -> ChartDataEntry {
        var entry: PieChartDataEntry;
        
        let item = values[index];
        
        if item.dictionary != nil {
            let dict = item;
            
            var value : Double;
            if dict["value"].double != nil {
                value = Double((dict["value"].doubleValue));
            } else {
                fatalError("invalid data " + values.description);
            }
            
            if dict["label"].string != nil {
                entry = PieChartDataEntry(value: value, label: dict["label"].stringValue)
            } else {
                entry = PieChartDataEntry(value: value)
            }
            
            entry.data = dict as AnyObject?
        } else if item.double != nil {
            entry = PieChartDataEntry(value : item.doubleValue);
        } else {
            fatalError("invalid data " + values.description);
        }
        
        return entry;
    }
}
