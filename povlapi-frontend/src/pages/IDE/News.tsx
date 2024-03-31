import { getMusicTop10UsingGET, hotNewListUsingGET, hotNewListUsingPOST } from "@/services/povlapi-backend/basicController";
import { PageContainer } from "@ant-design/pro-components";
import { Button, Card, Carousel, Col, Form, Input, Row } from "antd";
import { Content } from "antd/es/layout/layout";
import { useState } from "react";



const News: React.FC = () => {

    const [data, setData] = useState('');
    const [invokeLoading, setInvokeLoading] = useState(false)

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

    const handleSubmit = async () => {  
        try {  
          setInvokeLoading(true);
          const res = await hotNewListUsingGET();  
          if (res.data) {  
            setData(JSON.stringify(res.data, null, 2))
            console.log("data==>" + data);
          }  
        } catch (e) {  
          console.error(e);  
        }  
        setInvokeLoading(false);
    };

    const contentStyle: React.CSSProperties = {
      height: '160px',
      color: '#000000',
      lineHeight: '160px',
      textAlign: 'center',
    };

    return (
      <PageContainer>
        <div>
          <Carousel autoplay>
            <div>
              <img src="images/musk2.jpg" 
                  style = {{  width: '100%', height: '100%' }} />
              <h3 style={contentStyle}>马斯克和OpenAI的故事</h3>
            </div>
            <div>
              <img src="images/su7.png" 
                  style = {{  width: '100%', height: '100%' }} />
              <h3 style={contentStyle}>xiaomi汽车小米su7发布会</h3>
            </div>
            <div>
              <img src="images/ew.jpg" 
                  style = {{  width: '100%', height: '100%' }} />
              <h3 style={contentStyle}>俄乌局势何去何从？谁是最大赢家？</h3>    
            </div>
            <div>
              <img src="images/wa.jpg" 
                  style = {{  width: '100%', height: '100%' }} />
              <h3 style={contentStyle}>无畏契约全球冠军赛</h3>
            </div>
          </Carousel>
        </div>
        <div style={{ marginTop: '40px' }}>
          <Form {...formItemLayout}>
            <Form.Item style={{marginLeft: 250}}>
              <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic/news" readOnly />
            </Form.Item>
  
            <Form.Item 
              label="请求方法" 
              name="method" 
              style={{ maxWidth: 1000 }}
            >
              <Input defaultValue="GET" readOnly/>
            </Form.Item>

            <Form.Item
              label="响应结果"
              name="responseData"
              style={{ maxWidth: 1000 }}
            >
                <Card loading={invokeLoading}> 
                    {data}
                </Card>
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

export default News;