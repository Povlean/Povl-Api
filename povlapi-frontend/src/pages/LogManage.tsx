import React, { useEffect, useState } from 'react';
import { Avatar, List, Pagination } from 'antd';
import { getLogDataListUsingGET } from '@/services/povlapi-backend/logController';
import { PageContainer } from '@ant-design/pro-components';



const LogManage: React.FC = () => {

  const [list, setList] = useState<API.LogDataVO[]>([]);
  const [loading, setLoading] = useState(false);

  const loadData = async (current = 1, pageSize = 5) => {  
    setLoading(true);
    const response = await getLogDataListUsingGET();  
    console.log(response);  
    // 假设 response.data 是 API.LogDataVO[] 类型的数组，可能是 undefined  
    if (response && response.data) {  
      setList(response.data);  
    } else {  
      // 如果 response.data 是 undefined，则设置一个空数组  
      setList([]);  
    }  
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
  <PageContainer>
    <List
    itemLayout="horizontal"
    loading={loading}
    dataSource={list}
    renderItem={(item) => (
      <List.Item>
        <List.Item.Meta
          title={`操作账户：${item.userAccount}`}
          description={`操作记录：${item.operation}，操作时间：${item.createTime}`}
        />
      </List.Item>
    )}
    pagination={{
      // eslint-disable-next-line @typescript-eslint/no-shadow
      showTotal(total: number) {
        return '总数：' + total;
      },
      pageSize: 7,
      onChange(page, pageSize) {
        loadData(page, pageSize);
      },
    }}
    />
  </PageContainer>
  );
  
};

export default LogManage;