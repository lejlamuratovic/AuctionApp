import { createContext, useContext, useState } from 'react';

const UserNameContext = createContext(null);

export const useUserName = () => useContext(UserNameContext);

export const UserNamePorivder = ({ children }) => {
  const [userName, setUserName] = useState(null);

  return (
    <UserNameContext.Provider value={{ userName, setUserName }}>
      { children }
    </UserNameContext.Provider>
  );
};
