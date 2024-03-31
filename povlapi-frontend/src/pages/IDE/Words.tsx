import { getMusicTop10UsingGET, randomWordsUsingGET } from "@/services/povlapi-backend/basicController";
import { PageContainer } from "@ant-design/pro-components";
import { Button, Card, Carousel, Col, Form, Input, Row } from "antd";
import { useState } from "react";



const Music: React.FC = () => {

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
          const res = await randomWordsUsingGET();  
          if (res.data) {  
            setData(JSON.stringify(res.data, null, 2))
            console.log("data==>" + data);
          }  
        } catch (e) {  
          console.error(e);  
        }  
        setInvokeLoading(false);
    };

    const gridStyle: React.CSSProperties = {
      width: '25%',
      textAlign: 'center',
    };

    return (
      <PageContainer>
        <div style={{ marginBottom: '20px' }}>
            {/* <Row gutter={16}>
                <Col span={8}>
                    <Card title="坂本龙一和他的音乐旅途" bordered={false}>
                        <img src="images/img4.jpg" alt="Card Image 1" style={{ width: '100%', height: 'auto' }} />
                    </Card>
                </Col>
                <Col span={8}>
                    <Card title="Flower Dance" bordered={false}>
                        <img src="images/img3.jpg" alt="Card Image 1" style={{ width: '100%', height: 'auto' }} />
                    </Card>
                </Col>
                <Col span={8}>
                    <Card title="你的努力，云会知道" bordered={false}>
                        <img src="images/img5.jpg" alt="Card Image 1" style={{ width: '100%', height: 'auto' }} />
                    </Card>
                </Col>
            </Row> */}
              <Card title="经典名言">
                <Card.Grid style={gridStyle}>有一种鸟是笼子关不住的，因为它的每一片羽毛都洋溢着自由的光辉</Card.Grid>
                <Card.Grid style={gridStyle}>勇气是明知道会失败，但依然去坚定地去做</Card.Grid>
                <Card.Grid style={gridStyle}>瑶池本无树，明镜亦非台。世本无一物，何处惹尘埃</Card.Grid>
                <Card.Grid style={gridStyle}>存在，赋予了整个世界意义</Card.Grid>
                <Card.Grid style={gridStyle}>告别昨天的失利，迎接明天的机遇</Card.Grid>
                <Card.Grid style={gridStyle}>You waste time on your rose make it so important</Card.Grid>
                <Card.Grid style={gridStyle}>诗，美，浪漫，爱，这些才是我们生存的原因</Card.Grid>
                <Card.Grid style={gridStyle}>人生就像一场马拉松，不在乎起点，只在乎终点</Card.Grid>
              </Card>
        </div>  

        <div style={{ marginTop: '40px' }}>
          <Form {...formItemLayout}>
            <Form.Item style={{marginLeft: 250}}>
              <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic/words" readOnly />
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

export default Music;