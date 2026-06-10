import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import type { Mensagem } from '../types'

let client: Client | null = null

export async function desconectar() {
  if (client) {
    await client.deactivate()
    client = null
  }
}

export async function conectar(
  conversaId: number,
  onMensagem: (mensagem: Mensagem) => void
) {
  await desconectar()

  client = new Client({
    webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
    onConnect: () => {
      client?.subscribe(`/topic/conversa/${conversaId}`, msg => {
        const mensagem: Mensagem = JSON.parse(msg.body)
        onMensagem(mensagem)
      })
    },
  })

  client.activate()
}