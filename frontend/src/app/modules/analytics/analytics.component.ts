import { Component, OnInit } from '@angular/core';
import { EChartsOption, graphic } from 'echarts';
import { AnalyticsService } from '../../core/service/analytics.service';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css', '../home/home.component.css'],
})
export class AnalyticsComponent implements OnInit {

  constructor(private analyticsService: AnalyticsService) {
    this.analyticsService.getMonthlyExpenseStats()
      .subscribe((value) => {
        const expenseSeries = Array(12).fill(0);
        value.forEach(monthlyValue => {
          expenseSeries[monthlyValue.month - 1] = monthlyValue.value;
        });

        this.chartOption.series[1].data = expenseSeries;

        this.chartOption = { ...this.chartOption }; // To force refresh the plot
      });

    this.analyticsService.getMonthlyIncomeStats()
      .subscribe((value) => {
        const incomeSeries = Array(12).fill(0);
        value.forEach(monthlyValue => {
          incomeSeries[monthlyValue.month - 1] = monthlyValue.value;
        });

        this.chartOption.series[0].data = incomeSeries;

        this.chartOption = { ...this.chartOption }; // To force refresh the plot
      });
  }

  chartOption: EChartsOption = {
    title: {
      left: 'center',
      text: 'Yearly Analytics',
    },
    legend: {
      top: 'rightTop',
      data: ['Test'],
    },
    tooltip: {
      triggerOn: 'mousemove|click',
    },
    xAxis: {
      type: 'category',
      data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'].slice(0, new Date().getMonth() + 1),
      axisPointer: {
        snap: true,
        lineStyle: {
          color: '#7581BD',
          width: 2
        },
        label: {
          show: true,
          backgroundColor: '#7581BD'
        },
        handle: {
          show: true,
          size: 0,
          color: '#7581BD'
        }
      },
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        type: 'line',
        smooth: true,
        name: 'Income',
        areaStyle: {
          color: new graphic.LinearGradient(0, 0, 0, 1, [{
            offset: 0,
            color: 'rgb(100,61,171)',
          }, {
            offset: 1,
            color: 'white',
          }]),
        },
      },
      {
        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        type: 'line',
        smooth: true,
        name: 'Expense',
        areaStyle: {
          color: new graphic.LinearGradient(0, 0, 0, 1, [{
            offset: 0,
            color: 'rgb(121,212,253)',
          }, {
            offset: 1,
            color: 'white',
          }]),
        },
      },
    ],
  };

  ngOnInit(): void { }

}
