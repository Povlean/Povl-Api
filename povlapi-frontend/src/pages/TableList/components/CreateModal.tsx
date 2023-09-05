import {
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { Modal } from 'antd';
import React from 'react';

export type Props = {
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
};

const createModal: React.FC<Props> = (props) => {
  const { visible, columns, onCancel, onSubmit } = props;
  return (
    <Modal footer={null} open = {visible} onCancel={() => onCancel?.()}>
      <ProTable type="form" columns = {columns} onSubmit= {async(value) => {
        onSubmit?.(value); 
      }}/>
    </Modal>
  );
};

export default createModal;
