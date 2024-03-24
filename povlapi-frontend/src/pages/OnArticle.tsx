import React from 'react';
import {
  Button,
  Card,
  Cascader,
  Col,
  DatePicker,
  Form,
  Input,
  InputNumber,
  Mentions,
  Row,
  Select,
  TreeSelect,
  message,
} from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import { addPostUsingPOST } from '@/services/povlapi-backend/postController';



const OnArticle: React.FC = () => {

    const onSubmit = async (values: API.PostAddRequest) => {
        try {
          // 登录
          const res = await addPostUsingPOST({
            ...values,
          });
          if (res.data) {
            const defaultLoginSuccessMessage = '发布成功';
            message.success(defaultLoginSuccessMessage);
          }
        } catch (error) {
          const defaultLoginFailureMessage = '发布失败，请重试！';
          message.error('发布失败，请重试！' + error);
        }
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

    return(
        <PageContainer>
        <div style={{ marginBottom: '20px'}}>
            <Row gutter={16} align="middle" justify="center">
                <Col span={8}>
                    <Card bordered={false}>
                        <img src="images/littlePrince.jpg" alt="Card Image 1" style={{ width: '100%', height: 'auto' }} />
                    </Card>
                </Col>
                <Col span={8}>
                    <Card bordered={false}>
                        <img src="images/img7.jpg" alt="Card Image 1" style={{ width: '100%', height: 'auto' }} />
                    </Card>
                </Col>
            </Row>
        </div>
        <Form {...formItemLayout} 
            style={{ maxWidth: 1200 }}
            onFinish={async (values) => {
                onSubmit?.(values as API.PostAddRequest);
            }}
        >

        <Form.Item
        label="标题"
        name="title"
        rules={[{ required: true, message: '请输入标题!' }]}
        >
        <Mentions />
        </Form.Item>

        <Form.Item
        label="内容"
        name="content"
        rules={[{ required: true, message: '请输入内容!' }]}
        >
        <Input.TextArea style={{width: '100%', height: 200}}/>
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 12, span: 15 }}>
        <Button type="primary" htmlType="submit" >
            上传文章
        </Button>
        </Form.Item>
        </Form>
        </PageContainer>
    );

    

};
  
  


export default OnArticle;