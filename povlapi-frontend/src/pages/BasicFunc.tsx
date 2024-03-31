import React, { useState } from 'react';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Avatar, Card, Pagination, PaginationProps } from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import { Link } from '@umijs/max';

const { Meta } = Card;

const BasicFunc: React.FC = () => {

  return (
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
            description="用于查询未来七天的最高最低气温"
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
            title="音乐推荐接口"
            description="用于推荐网易云音乐的最热音乐"
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
            title="图书查询接口"
            description="使用图书的isbn编号查询书名、作家、年份、书籍内容等数据"
          />
        </Card>
      </div>
      <Link to="/basicFunc2" style={{marginLeft: '950px' }}>
        下一页
      </Link> 
    </PageContainer>
  );

};

export default BasicFunc;