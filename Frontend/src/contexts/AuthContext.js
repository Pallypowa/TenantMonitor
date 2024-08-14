const AuthContext = React.createContext()

export function useAuth() {
    return React.useContext(AuthContext)
}

export function AuthProvider({ children }) {
    return <AuthContext.Provider>{children}</AuthContext.Provider>
}
