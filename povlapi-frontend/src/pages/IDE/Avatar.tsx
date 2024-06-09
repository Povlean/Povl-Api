import { getMusicTop10UsingGET, getRandomHeadUsingGET, searchBookByIsbnUsingGET } from "@/services/povlapi-backend/basicController";
import { PageContainer } from "@ant-design/pro-components";
import { Button, Card, Carousel, Col, Form, Input, Row, message } from "antd";
import { useEffect, useRef, useState } from "react";



const Avatar: React.FC = () => {
    const paramsRef = useRef({ isbnNum: '' }); // 初始化ref
    const [data, setData] = useState('');
    const [invokeLoading, setInvokeLoading] = useState(false)
    const [isbnNum, setIsbnNum] = useState('');

      // 当cityName变化时，更新paramsRef中的cityName属性  
    useEffect(() => {  
      paramsRef.current.isbnNum = isbnNum; // 只更新cityName属性  
    }, [isbnNum]); // 依赖项中包含cityName  

    const handleTextAreaChange = (e: any) => {  
      setIsbnNum(e.target.value); // 更新cityName状态  
    };

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
        const res = await getRandomHeadUsingGET();
        console.log("res===>" + res);
        if (res.data) {
          setInvokeLoading(true);
          setData(res.data);
        }
        setInvokeLoading(false);
      } catch (e: any) {
        message.error(e)
      }
    };



    return (
      <PageContainer>
        <div style={{ marginBottom: '20px', display: 'center', marginLeft: '400px' }}>
            <Row gutter={16}>
                <Col span={8}>
                    <Card title="随机头像" bordered={false}>
                        <img src={data} alt="Card Image 1" style={{ width: '100%', height: 'auto'}} />
                    </Card>
                </Col>
            </Row>
        </div>  

        <div style={{ marginTop: '40px' }}>
          <Form {...formItemLayout}>
            <Form.Item style={{marginLeft: 250}}>
              <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic/head" readOnly />
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

export default Avatar;