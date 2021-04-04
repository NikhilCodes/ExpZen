import { Component, OnInit } from '@angular/core';
import { EChartsOption, graphic } from 'echarts';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css', '../home/home.component.css'],
})
export class AnalyticsComponent implements OnInit {

  constructor() { }

  chartOption: EChartsOption = {
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: 'line',
        smooth: true,
        name: 'Income',
        areaStyle: {
          color: new graphic.LinearGradient(0, 0, 0, 1, [{
            offset: 0,
            color: 'rgb(100,61,171)'
          }, {
            offset: 1,
            color: 'white'
          }])
        },
      },
      {
        data: [920, 232, 501, 634, 1260, 1430, 1620],
        type: 'line',
        smooth: true,
        name: 'Expense',
        areaStyle: {
          color: new graphic.LinearGradient(0, 0, 0, 1, [{
            offset: 0,
            color: 'rgb(121,212,253)'
          }, {
            offset: 1,
            color: 'white'
          }])
        },
      },
    ],
  };

  ngOnInit(): void {
  }

}
