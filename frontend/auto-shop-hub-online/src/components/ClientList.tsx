
import { useEffect, useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { User, Phone, Mail, MapPin } from "lucide-react";
import { apiService } from "@/services/api";
import { useToast } from "@/hooks/use-toast";

interface Client {
  id: string;
  name: string;
  socialSecurity: string;
  phone: string;
  email: string;
  address: string;
  number: string;
  complement: string;
  neighborhood: string;
  city: string;
  state: string;
}

const ClientList = () => {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  const fetchClients = async () => {
    try {
      setLoading(true);
      const response = await apiService.getClients();
      setClients(response);
    } catch (error: any) {
      console.error('Erro ao buscar clientes:', error);
      toast({
        title: "Erro",
        description: "Não foi possível carregar os clientes",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchClients();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="text-gray-500">Carregando clientes...</div>
      </div>
    );
  }

  return (
    <div className="space-y-4 max-h-96 overflow-y-auto">
      {clients.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          Nenhum cliente cadastrado ainda.
        </div>
      ) : (
        clients.map((client) => (
          <Card key={client.id} className="hover:shadow-md transition-shadow">
            <CardHeader className="pb-2">
              <CardTitle className="flex items-center space-x-2">
                <User className="h-4 w-4" />
                <span className="text-lg">{client.name}</span>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-2">
              <div className="space-y-1 text-sm">
                <div className="flex items-center space-x-1">
                  <Phone className="h-3 w-3 text-gray-500" />
                  <span>{client.phone}</span>
                </div>
                {client.email && (
                  <div className="flex items-center space-x-1">
                    <Mail className="h-3 w-3 text-gray-500" />
                    <span>{client.email}</span>
                  </div>
                )}
                {client.address && (
                  <div className="flex items-center space-x-1">
                    <MapPin className="h-3 w-3 text-gray-500" />
                    <span>{client.address}, {client.neighborhood}</span>
                  </div>
                )}
              </div>
              <div className="text-xs text-gray-500">
                CPF: {client.socialSecurity} | {client.city}/{client.state}
              </div>
            </CardContent>
          </Card>
        ))
      )}
    </div>
  );
};

export default ClientList;
