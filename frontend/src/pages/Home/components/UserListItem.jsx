import { useState } from "react";

export function UserListItem({ user }) {
  const [selected, setSelected] = useState(false);

  return (
    <li
      className={`list-group-item list-group-item-action ${selected ? 'active':''}`}
      onClick={() => setSelected(!selected)}>
      {user.username}
    </li>
  );
}
