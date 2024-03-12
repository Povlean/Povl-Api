import React from 'react';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Avatar, Card } from 'antd';

const { Meta } = Card;

const App: React.FC = () => (
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
        <EllipsisOutlined key="ellipsis" />,
      ]}
    >
      <Meta
        avatar={<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8" />}
        title="Card title"
        description="This is the description"
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
        <EllipsisOutlined key="ellipsis" />,
      ]}
    >
      <Meta
        avatar={<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8" />}
        title="Card title"
        description="This is the description"
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
        <EllipsisOutlined key="ellipsis" />,
      ]}
    >
      <Meta
        avatar={<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8" />}
        title="Card title"
        description="This is the description"
      />
    </Card>
  </div>
  
);

export default App;