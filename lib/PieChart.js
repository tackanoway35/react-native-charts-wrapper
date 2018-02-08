import PropTypes from 'prop-types';
import React, { Component } from 'react';
import {
  requireNativeComponent,
  View
} from 'react-native';

import PieRadarChartBase from './PieRadarChartBase';
import { pieData } from './ChartDataConfig';

class PieChart extends React.Component {
  getNativeComponentName() {
    return 'RNPieChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNPieChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }
}

PieChart.propTypes = {
  ...PieRadarChartBase.propTypes,

  drawEntryLabels: PropTypes.bool,
  extraOffsets: PropTypes.array,
  usePercentValues: PropTypes.bool,
  xValuePosition: PropTypes.bool,

  centerText: PropTypes.string,
  styledCenterText: PropTypes.shape({
    text: PropTypes.string,
    color: PropTypes.number,
    size: PropTypes.number
  }),
  centerTextRadiusPercent: PropTypes.number,
  holeRadius: PropTypes.number,
  holeColor: PropTypes.number,
  transparentCircleRadius: PropTypes.number,
  transparentCircleColor: PropTypes.number,

  entryLabelColor: PropTypes.number,
  entryLabelTextSize: PropTypes.number,
  maxAngle: PropTypes.number,
  valueLineWidth: PropTypes.number,
  valueLinePart1Length: PropTypes.number,
  valueLinePart2Length: PropTypes.number,
  valueLineColor: PropTypes.string,

  // TODO PieChart should have only one dataset
  data: pieData
};

var RNPieChart = requireNativeComponent('RNPieChart', PieChart, {
  nativeOnly: { onSelect: true, onChange: true }
});

export default PieChart
