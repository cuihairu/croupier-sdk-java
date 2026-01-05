import { defineUserConfig } from 'vuepress'
import { defaultTheme } from '@vuepress/theme-default'
import { viteBundler } from '@vuepress/bundler-vite'
import { searchPlugin } from '@vuepress/plugin-search'

export default defineUserConfig({
  lang: 'zh-CN',
  title: 'Croupier Java SDK',
  description: 'Java SDK for Croupier Game Backend Platform',
  head: [
    ['meta', { name: 'viewport', content: 'width=device-width,initial-scale=1' }],
    ['meta', { name: 'keywords', content: 'croupier,java,sdk,gRPC,游戏开发' }],
    ['meta', { name: 'theme-color', content: '#0074BD' }],
  ],
  base: '/',
  bundler: viteBundler(),
  theme: defaultTheme({
    repo: 'cuihairu/croupier-sdk-java',
    repoLabel: 'GitHub',
    navbar: [
      { text: '指南', link: '/guide/' },
      { text: 'API 参考', link: '/api/' },
      { text: '示例', link: '/examples/' },
      {
        text: '其他 SDK',
        children: [
          { text: 'C++', link: 'https://cuihairu.github.io/croupier-sdk-cpp/' },
          { text: 'Go', link: 'https://cuihairu.github.io/croupier-sdk-go/' },
          { text: 'JavaScript', link: 'https://cuihairu.github.io/croupier-sdk-js/' },
          { text: 'Python', link: 'https://cuihairu.github.io/croupier-sdk-python/' },
        ],
      },
    ],
    sidebar: {
      '/': [
        '/README.md',
        { text: '指南', children: ['/guide/README.md', '/guide/quick-start.md'] },
        { text: 'API', children: ['/api/README.md'] },
      ],
    },
  }),
  plugins: [
    searchPlugin({
      locales: {
        '/': { placeholder: '搜索文档' },
      },
    }),
  ],
})
