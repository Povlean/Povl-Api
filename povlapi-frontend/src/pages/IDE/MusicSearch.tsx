import { getMusicTop10UsingGET, musicInfoUsingPOST } from "@/services/povlapi-backend/basicController";
import { PageContainer } from "@ant-design/pro-components";
import { Button, Card, Carousel, Col, Form, Input, Row } from "antd";
import { useEffect, useRef, useState } from "react";



const MusicSearch: React.FC = () => {

    const paramsRef = useRef({ id: '' }); // 初始化ref
    const [id, setId] = useState('');
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

    useEffect(() => {  
      paramsRef.current.id = id; // 只更新cityName属性  
    }, [id]); // 依赖项中包含cityName  

    const handleTextAreaChange = (e: any) => {  
      setId(e.target.value); // 更新cityName状态  
    };

    const handleSubmit = async () => {  
        try {  
          setInvokeLoading(true);
          const res = await musicInfoUsingPOST(paramsRef.current);  
          if (res.data) {  
            setData(JSON.stringify(res.data, null, 2))
            console.log("data==>" + data);
          }  
        } catch (e) {  
          console.error(e);  
        }  
        setInvokeLoading(false);
    };

    return (
      <PageContainer>
        <div style={{ marginBottom: '20px' }}>
            <Row gutter={16}>
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
            </Row>
        </div>  

        <div style={{ marginTop: '40px' }}>
          <Form {...formItemLayout}>
            <Form.Item style={{marginLeft: 250}}>
              <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic//music/{id}" readOnly />
            </Form.Item>
  
            <Form.Item 
              label="请求方法" 
              name="method" 
              style={{ maxWidth: 1000 }}
            >
              <Input defaultValue="GET" readOnly/>
            </Form.Item>

            <Form.Item
            label="请求参数（填写音乐id即可）"
            name="TextArea"
            rules={[{ required: true, message: '请填写音乐id' }]}
            style={{ maxWidth: 1000 }}
            >
              <Input.TextArea onChange={handleTextAreaChange} value={id} placeholder="如海阔天空：1357375695" />
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

export default MusicSearch;