import { useParams } from "react-router-dom"

export function Activation() {
    const {token} = useParams()
    return <div>Activation token :{token}</div>
}