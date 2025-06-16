
import { useEffect, useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Car, Fuel, Calendar, DollarSign } from "lucide-react";
import { apiService } from "@/services/api";
import { useToast } from "@/hooks/use-toast";

interface Vehicle {
  id: string;
  brand: string;
  model: string;
  year: string;
  color: string;
  plate: string;
  chassis: string;
  fuelType: string;
  price: string;
  status: string;
  released: boolean;
}

const VehicleList = () => {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  const fetchVehicles = async () => {
    try {
      setLoading(true);
      const response = await apiService.getVehicles();
      setVehicles(response);
    } catch (error: any) {
      console.error('Erro ao buscar veículos:', error);
      toast({
        title: "Erro",
        description: "Não foi possível carregar os veículos",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchVehicles();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-8">
        <div className="text-gray-500">Carregando veículos...</div>
      </div>
    );
  }

  return (
    <div className="space-y-4 max-h-96 overflow-y-auto">
      {vehicles.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          Nenhum veículo cadastrado ainda.
        </div>
      ) : (
        vehicles.map((vehicle) => (
          <Card key={vehicle.id} className="hover:shadow-md transition-shadow">
            <CardHeader className="pb-2">
              <CardTitle className="flex items-center justify-between">
                <span className="text-lg">{vehicle.brand} {vehicle.model}</span>
                <Badge variant={vehicle.released ? "default" : "secondary"}>
                  {vehicle.released ? "Disponível" : "Vendido"}
                </Badge>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-2">
              <div className="grid grid-cols-2 gap-2 text-sm">
                <div className="flex items-center space-x-1">
                  <Calendar className="h-3 w-3 text-gray-500" />
                  <span>{vehicle.year}</span>
                </div>
                <div className="flex items-center space-x-1">
                  <Car className="h-3 w-3 text-gray-500" />
                  <span>{vehicle.color}</span>
                </div>
                <div className="flex items-center space-x-1">
                  <Fuel className="h-3 w-3 text-gray-500" />
                  <span>{vehicle.fuelType}</span>
                </div>
                <div className="flex items-center space-x-1">
                  <DollarSign className="h-3 w-3 text-gray-500" />
                  <span>R$ {parseInt(vehicle.price).toLocaleString()}</span>
                </div>
              </div>
              <div className="text-xs text-gray-500">
                Placa: {vehicle.plate} | Status: {vehicle.status}
              </div>
            </CardContent>
          </Card>
        ))
      )}
    </div>
  );
};

export default VehicleList;
