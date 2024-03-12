import { invokeInterfaceInfoUsingPOST } from '@/services/povlapi-backend/interfaceInfoController';
import { getTopInvokeInterfaceUsingGET } from '@/services/povlapi-backend/analysisController';
import { HeartTwoTone, SmileTwoTone } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Alert, Card, Typography } from 'antd';
import ReactECharts from 'echarts-for-react';
import React, { useEffect, useState } from 'react';

const Analysis: React.FC = () => {

  const [data, setData] = useState<API.InterfaceAnalVO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(()=>{
    try {
      getTopInvokeInterfaceUsingGET().then(res => {
        if (res.data) {
          setData(res.data);
        }
      })
    } catch (e:any) {

    }

  }, [])

  const chartData = data.map(item => {
    return {
      value: item.totalNum,
      name: item.interfaceName,
    }
  })

  const val = chartData.map(item => item.value)
  const name = chartData.map(item => item.name)

  const option1 = {
    title: {
      text: '接口调用情况',
      subtext: '饼状图分析',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };

  const option2 = {
    title: {
      text: '接口调用情况',
      subtext: '柱状图分析',
      left: 'center'
    },
    xAxis: {
      type: 'category',
      data: name
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: val,
        type: 'bar'
      }
    ]
  };
  return (
    <PageContainer>
      <ReactECharts option={option1} />
      <ReactECharts option={option2} />
    </PageContainer>
  );
};

export default Analysis;
