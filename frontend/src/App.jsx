import { Button, Typography, Space } from 'antd'

const { Title, Text } = Typography

function App() {
  return (
    <Space
      direction="vertical"
      align="center"
      style={{ width: '100%', paddingTop: 80 }}
    >
      <Title>Jira Mini</Title>
      <Text type="secondary">Project management for teams</Text>
      <Button type="primary">Get Started</Button>
    </Space>
  )
}

export default App
