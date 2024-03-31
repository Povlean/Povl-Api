import { getMusicTop10UsingGET, searchBookByIsbnUsingGET, stockUsingGET } from "@/services/povlapi-backend/basicController";
import { PageContainer } from "@ant-design/pro-components";
import { Button, Card, Carousel, Col, Form, Input, Row } from "antd";
import { useEffect, useRef, useState } from "react";



const Stock: React.FC = () => {
    const paramsRef = useRef({ symbol: '' }); // 初始化ref
    const [data, setData] = useState('');
    const [invokeLoading, setInvokeLoading] = useState(false)
    const [symbol, setSymbol] = useState('');

    useEffect(() => {  
      paramsRef.current.symbol = symbol; 
    }, [symbol]);

    const handleTextAreaChange = (e: any) => {  
      setSymbol(e.target.value);
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
          setInvokeLoading(true);
          const res = await stockUsingGET(paramsRef.current);  
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
        <div style={{ marginTop: '40px' }}>
          <Form {...formItemLayout}>
            <Form.Item style={{marginLeft: 250}}>
              <Input addonBefore="http://" suffix=".com" defaultValue="192.168.43.59:7529/api/basic/stock/{symbol}" readOnly />
            </Form.Item>
  
            <Form.Item 
              label="请求方法" 
              name="method" 
              style={{ maxWidth: 1000 }}
            >
              <Input defaultValue="GET" readOnly/>
            </Form.Item>

            <Form.Item
            label="请求参数（填写证券号即可）"
            name="TextArea"
            rules={[{ required: true, message: '请输入证券号' }]}
            style={{ maxWidth: 1000 }}
            >
              <Input.TextArea onChange={handleTextAreaChange} value={symbol} />
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

export default Stock;