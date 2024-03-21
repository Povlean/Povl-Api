import { weatherConditionUsingPOST } from '@/services/povlapi-backend/basicController';
import { PageContainer } from '@ant-design/pro-components';
import Search from 'antd/es/input/Search';
import ReactECharts from 'echarts-for-react';
import { useEffect, useRef, useState } from 'react';
import React from 'react';
import {
  Button,
  Cascader,
  DatePicker,
  Form,
  Input,
  InputNumber,
  Mentions,
  Select,
  TreeSelect,
} from 'antd';

const Weather: React.FC = () => {
  const [cityName, setCityName] = useState(''); // 初始化cityName状态
  const [data, setData] = useState<API.WeatherVO | null>(null);  
  const [tempMaxArray, setTempMaxArray] = useState<string[]>([]);
  const [tempMinArray, setTempMinArray] = useState<string[]>([]);
  const [textDayArray, setTextDayArray] = useState<string[]>([]);
  const [fxDateArray, setFxDateArray] = useState<string[]>([]);
  const paramsRef = useRef({ cityName: '' }); // 初始化ref

  const formItemLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 14 },
    },
  };
  useEffect(() => {
    const fetchData = async () => {
      try {  
        paramsRef.current.cityName = cityName;
        const res = await weatherConditionUsingPOST(paramsRef.current);  
        if (res.data) {  
          setData(res.data);  
          processData(res.data);  
        }  
      } catch (e) {  
        console.error(e);  
      }  
    };  
    fetchData();  
  }, []); // 依赖项为空数组，表示只在组件挂载时执行一次  
  const processData = (weatherData: API.WeatherVO) => {  
    const forecastVOList = weatherData.forecastVOList;  
    const tempMaxArray: string[] = [];  
    const tempMinArray: string[] = [];  
    const textDayArray: string[] = [];  
    const fxDateArray: string[] = [];  
  
    if (forecastVOList) {  
      forecastVOList.forEach(item => {  
        if (item.tempMax) tempMaxArray.push(item.tempMax.toString());  
        if (item.tempMin) tempMinArray.push(item.tempMin.toString());  
        if (item.textDay) textDayArray.push(item.textDay);  
        if (item.fxDate) fxDateArray.push(item.fxDate);  
      });  
    }  
    setTempMaxArray(tempMaxArray);  
    setTempMinArray(tempMinArray);  
    setTextDayArray(textDayArray);  
    setFxDateArray(fxDateArray);  
  };

  const handleTextAreaChange = (e: any) => {  
    setCityName(e.target.value); // 更新cityName状态  
  };

  const handleSubmit = () => {  
    // 这里可以使用paramsRef.current来发送请求  
    // 例如：API.weatherConditionUsingPOST(paramsRef.current)  
    weatherConditionUsingPOST(paramsRef.current);
  };
  
  const option = {
    title: {
      text: '未来一周的天气'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {},
    toolbox: {
      show: true,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        dataView: { readOnly: false },
        magicType: { type: ['line', 'bar'] },
        restore: {},
        saveAsImage: {}
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: fxDateArray
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} °C'
      }
    },
    series: [
      {
        name: 'Highest',
        type: 'line',
        data: tempMaxArray,
        markPoint: {},
        markLine: {}
      },
      {
        name: 'Lowest',
        type: 'line',
        data: tempMinArray,
        markPoint: {},
        markLine: {}
      }
    ]
  };
  
  
  return (
    <PageContainer>
      <div>  
        <ReactECharts option={option}/>
      </div>  
      <div>
        <Form {...formItemLayout}>
          <Form.Item style={{marginLeft: 250}}>
            <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic/weather/{cityName}" />
          </Form.Item>

          <Form.Item 
            label="请求方法" 
            name="method" 
            style={{ maxWidth: 1000 }}
          >
            <Input defaultValue="POST"/>
          </Form.Item>

          <Form.Item
            label="请求参数（填写城市名即可）"
            name="TextArea"
            rules={[{ required: true, message: 'Please input!' }]}
            style={{ maxWidth: 1000 }}
          >
            <Input.TextArea onChange={handleTextAreaChange} value={cityName} />
          </Form.Item>

          <Form.Item wrapperCol={{ offset: 12, span: 16 }} style={{ maxWidth: 1000 }}>
            <Button type="primary" htmlType="submit" onClick={handleSubmit}>
              调用
            </Button>
          </Form.Item>
        </Form>
      </div>
    </PageContainer>
  );
};

export default Weather;