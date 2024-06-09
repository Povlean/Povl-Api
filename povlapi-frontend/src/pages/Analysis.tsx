import { invokeInterfaceInfoUsingPOST } from '@/services/povlapi-backend/interfaceInfoController';
import { getDailyInterfaceUsingGET, getDailyLoginNumUsingGET, getOperationTimeUsingGET, getTopInvokeInterfaceUsingGET } from '@/services/povlapi-backend/analysisController';
import { ArrowDownOutlined, ArrowUpOutlined, HeartTwoTone, SmileTwoTone } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Alert, Card, Col, Row, Statistic, Typography } from 'antd';
import ReactECharts from 'echarts-for-react';
import React, { useEffect, useState } from 'react';
import moment from 'moment';
import Countdown from 'antd/es/statistic/Countdown';

const Analysis: React.FC = () => {
  const [data, setData] = useState<API.InterfaceAnalVO[]>([]);
  const [data1, setData1] = useState<API.UsingInterfaceCountVO[]>([]);
  const [opTime, setOpTime] = useState(null); // 初始操作时间为 null  
  const [timeElapsed, setTimeElapsed] = useState(0);  

  useEffect(()=>{
    try {
      getTopInvokeInterfaceUsingGET().then(res => {
        if (res.data) {
          console.log("data==>" + res.data);
          setData(res.data);
        }
      })
      getDailyLoginNumUsingGET().then(res => {
        if (res.data) {
          setData1(res.data);
        }
      }) 
      const intervalId = setInterval(async() => {  
        try {
          const res = await getOperationTimeUsingGET();
          const newOpTime = res.data;
          setOpTime(newOpTime); 
           // 计算经过时间（当前时间 - 操作时间）  
          const currentTime = Date.now();  
          const elapsedTime = currentTime - newOpTime;  
          setTimeElapsed(elapsedTime); // 设置新的经过时间状态  
          console.log("elapsedTime==>" + elapsedTime);
          setTimeElapsed(elapsedTime);  
        } catch (e: any) {
          console.error('Error fetching operation time:', e); 
        }
      }, 1000); // 每秒更新一次  
      return () => {  
        clearInterval(intervalId);  
      };
    } catch (e:any) {
      console.log(e)
    }

  }, [])

  const chartData = data.map(item => {
    return {
      value: item.count,
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

  
  const chartData1 = data1.map(item => {
    console.log(item)
    return {
      date: item.date,
      count: item.count,
    }
  })

  const date = chartData1.map(item => item.date)
  const count = chartData1.map(item => item.count)

  const option3 = {
    title: {
      text: '每日登录总数',
      subtext: '折线图分析',
      left: 'center'
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: date
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: count,
        type: 'line',
        areaStyle: {}
      }
    ]
  };
  
  const formatTime = (milliseconds: number) => {  
    const duration = moment.duration(milliseconds);  
    return `${Math.floor(duration.asDays())} 天 ${duration.hours()} 时 ${duration.minutes()} 分 ${duration.seconds()} 秒`;  
  }; 

  return (  
    <div>  
      <PageContainer>
        <div style={{width: '50%', display: 'inline-block'}}>
          <Row gutter={16}>  
            <Col span={24}>  
              <Countdown  title="网站经营时间" value={timeElapsed} format={formatTime(timeElapsed)} />  
            </Col>
          </Row>  
        </div>
        <div style={{width: '50%', display: 'inline-block'}}>
          <Row gutter={16}>
            <Col span={12}>
              <Card bordered={true}>
                <Statistic
                  title="社区活跃度"
                  value={11.28}
                  precision={2}
                  valueStyle={{ color: '#3f8600' }}
                  prefix={<ArrowUpOutlined />}
                  suffix="%"
                />
              </Card>
            </Col>
            <Col span={12}>
              <Card bordered={true}>
                <Statistic
                  title="社区活跃度"
                  value={9.3}
                  precision={2}
                  valueStyle={{ color: '#cf1322' }}
                  prefix={<ArrowUpOutlined />}
                  suffix="%"
                />
              </Card>
            </Col>
          </Row>
        </div>
      </PageContainer>
      <PageContainer>  
        <div style={{ width: '50%', display: 'inline-block' }}>  
          <ReactECharts option={option1} />  
        </div>  
        <div style={{ width: '50%', display: 'inline-block' }}>  
          <ReactECharts option={option2} />  
        </div>  
      </PageContainer>  
      <PageContainer>
        <div>
          <ReactECharts option={option3}/>
        </div>
      </PageContainer>
    </div>  
  );  
};

export default Analysis;
