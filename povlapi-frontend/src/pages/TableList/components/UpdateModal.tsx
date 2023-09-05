import {
  ProColumns,
  ProFormInstance,
  ProTable,
} from '@ant-design/pro-components';
import { Modal } from 'antd';
import React, { useEffect, useRef } from 'react';

export type Props = {
  values: API.InterfaceInfo;
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
};

const updateModal: React.FC<Props> = (props) => {
  const { values, visible, columns, onCancel, onSubmit } = props;

  const formRef = useRef<ProFormInstance>();

  useEffect(() => {
    if (formRef) {
      formRef.current?.setFieldsValue(values);
    }
  }, [values])

  return (
    <Modal footer={null} open = {visible} onCancel={() => onCancel?.()}>
      <ProTable 
        type="form" 
        formRef={formRef}
        columns = {columns}  
        onSubmit= {async(value) => {
          onSubmit?.(value);
        }}
      form={{
        initialValues: values
      }}
      />
    </Modal>
  );
};

export default updateModal;
