import React from 'react';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Avatar, Card } from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import { Link } from '@umijs/max';

const { Meta } = Card;

const BasicFunc: React.FC = () => (
  
  <PageContainer title='集成接口测试'>
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <Card
        style={{ width: 300, margin: 20}}
        cover={
          <img
            alt="example"
            src="/images/solarTerm03.png"
          />
        }
        actions={[
          <Link to="/weather">
            体验
          </Link> 
        ]}
      >
        <Meta
          title="天气查询接口"
          description="用于查询降水、天气、运动指数等数据的接口--该接口由和风天气API提供"
        />
      </Card>

      <Card
        style={{ width: 300, margin: 20}}
        cover={
          <img
            alt="example"
            src="/images/music01.png"
          />
        }
        actions={[
          <Link to="/music">
            体验
          </Link> 
        ]}
      >
        <Meta
          title="音乐查询接口"
          description="用于查询音乐名、专辑名、作者等数据的接口--该接口由网易云音乐API提供"
        />
      </Card>

      <Card
        style={{ width: 300, margin: 20}}
        cover={
          <img
            alt="example"
            src="/images/lp.jpg"
          />
        }
        actions={[
          <Link to="/book">
            体验
          </Link> 
        ]}
      >
        <Meta
          title="图书推荐接口"
          description="用于查询书名、作家、年份、书籍内容等数据的接口--该接口由豆瓣API提供"
        />
      </Card>
    </div>
  </PageContainer>
);

export default BasicFunc;