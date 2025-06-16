import { useEffect, useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { UserCheck, Phone, Mail, Crown } from "lucide-react";
import { apiService } from "@/services/api";
import { useToast } from "@/hooks/use-toast";

const SellerList = () => {
  const [sellers, setSellers] = useState<Awaited<ReturnType<typeof apiService.getSellers>>>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  const fetchSellers = async () => {
    try {
      setLoading(true);
      const response = await apiService.getSellers();
      setSellers(response);
    } catch (error: any) {
      console.error('Erro ao buscar vendedores:', error);
      toast({
        title: "Erro",
        description: "Não foi possível carregar os vendedores",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSellers();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="text-gray-500">Carregando vendedores...</div>
      </div>
    );
  }

  return (
    <div className="space-y-4 max-h-96 overflow-y-auto">
      {sellers.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          Nenhum vendedor cadastrado ainda.
        </div>
      ) : (
        sellers.map((seller) => (
          <Card key={seller.id} className="hover:shadow-md transition-shadow">
            <CardHeader className="pb-2">
              <CardTitle className="flex items-center justify-between">
                <div className="flex items-center space-x-2">
                  <UserCheck className="h-4 w-4" />
                  <span className="text-lg">{seller.name}</span>
                </div>
                {seller.manager && (
                  <Badge variant="default" className="bg-yellow-500 hover:bg-yellow-600">
                    <Crown className="h-3 w-3 mr-1" />
                    Gerente
                  </Badge>
                )}
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-2">
              <div className="space-y-1 text-sm">
                <div className="flex items-center space-x-1">
                  <Phone className="h-3 w-3 text-gray-500" />
                  <span>{seller.phone}</span>
                </div>
                {seller.email && (
                  <div className="flex items-center space-x-1">
                    <Mail className="h-3 w-3 text-gray-500" />
                    <span>{seller.email}</span>
                  </div>
                )}
              </div>
              <div className="text-xs text-gray-500">
                CPF: {seller.cpf}
              </div>
            </CardContent>
          </Card>
        ))
      )}
    </div>
  );
};

export default SellerList;
