
import { useState } from "react";
import { Car, Users, UserCheck, Plus, CreditCard, ShoppingCart } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import VehicleForm from "@/components/VehicleForm";
import ClientForm from "@/components/ClientForm";
import SellerForm from "@/components/SellerForm";
import SaleForm from "@/components/SaleForm";
import PaymentForm from "@/components/PaymentForm";
import VehicleList from "@/components/VehicleList";
import ClientList from "@/components/ClientList";
import SellerList from "@/components/SellerList";
import SaleList from "@/components/SaleList";
import PaymentList from "@/components/PaymentList";

const Index = () => {
  const [activeTab, setActiveTab] = useState("vehicles");

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Header */}
      <header className="bg-white shadow-lg border-b">
        <div className="container mx-auto px-4 py-6">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <div className="bg-blue-600 p-2 rounded-lg">
                <Car className="h-8 w-8 text-white" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">AutoVendas</h1>
                <p className="text-sm text-gray-600">Sistema de Gestão Veicular</p>
              </div>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8">
        <div className="mb-8">
          <h2 className="text-3xl font-bold text-gray-900 mb-2">
            Gerencie seu Negócio Automotivo
          </h2>
          <p className="text-lg text-gray-600">
            Sistema completo para gestão de veículos, clientes, vendedores, vendas e pagamentos
          </p>
        </div>

        <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
          <TabsList className="grid w-full grid-cols-5 lg:w-[1000px] mx-auto mb-8">
            <TabsTrigger value="vehicles" className="flex items-center space-x-2">
              <Car className="h-4 w-4" />
              <span>Veículos</span>
            </TabsTrigger>
            <TabsTrigger value="clients" className="flex items-center space-x-2">
              <Users className="h-4 w-4" />
              <span>Clientes</span>
            </TabsTrigger>
            <TabsTrigger value="sellers" className="flex items-center space-x-2">
              <UserCheck className="h-4 w-4" />
              <span>Vendedores</span>
            </TabsTrigger>
            <TabsTrigger value="sales" className="flex items-center space-x-2">
              <ShoppingCart className="h-4 w-4" />
              <span>Vendas</span>
            </TabsTrigger>
            <TabsTrigger value="payments" className="flex items-center space-x-2">
              <CreditCard className="h-4 w-4" />
              <span>Pagamentos</span>
            </TabsTrigger>
          </TabsList>

          {/* Vehicles Tab */}
          <TabsContent value="vehicles" className="space-y-6">
            <div className="grid lg:grid-cols-2 gap-6">
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center space-x-2">
                    <Plus className="h-5 w-5" />
                    <span>Cadastrar Veículo</span>
                  </CardTitle>
                  <CardDescription>
                    Adicione um novo veículo ao estoque
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <VehicleForm />
                </CardContent>
              </Card>

              <Card>
                <CardHeader>
                  <CardTitle>Veículos em Estoque</CardTitle>
                  <CardDescription>
                    Lista de todos os veículos cadastrados
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <VehicleList />
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          {/* Clients Tab */}
          <TabsContent value="clients" className="space-y-6">
            <div className="grid lg:grid-cols-2 gap-6">
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center space-x-2">
                    <Plus className="h-5 w-5" />
                    <span>Cadastrar Cliente</span>
                  </CardTitle>
                  <CardDescription>
                    Adicione um novo cliente ao sistema
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <ClientForm />
                </CardContent>
              </Card>

              <Card>
                <CardHeader>
                  <CardTitle>Lista de Clientes</CardTitle>
                  <CardDescription>
                    Todos os clientes cadastrados
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <ClientList />
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          {/* Sellers Tab */}
          <TabsContent value="sellers" className="space-y-6">
            <div className="grid lg:grid-cols-2 gap-6">
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center space-x-2">
                    <Plus className="h-5 w-5" />
                    <span>Cadastrar Vendedor</span>
                  </CardTitle>
                  <CardDescription>
                    Adicione um novo vendedor à equipe
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <SellerForm />
                </CardContent>
              </Card>

              <Card>
                <CardHeader>
                  <CardTitle>Equipe de Vendas</CardTitle>
                  <CardDescription>
                    Todos os vendedores cadastrados
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <SellerList />
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          {/* Sales Tab */}
          <TabsContent value="sales" className="space-y-6">
            <div className="grid lg:grid-cols-2 gap-6">
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center space-x-2">
                    <Plus className="h-5 w-5" />
                    <span>Cadastrar Venda</span>
                  </CardTitle>
                  <CardDescription>
                    Registre uma nova venda no sistema
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <SaleForm />
                </CardContent>
              </Card>

              <Card>
                <CardHeader>
                  <CardTitle>Lista de Vendas</CardTitle>
                  <CardDescription>
                    Todas as vendas registradas
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <SaleList />
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          {/* Payments Tab */}
          <TabsContent value="payments" className="space-y-6">
            <div className="grid lg:grid-cols-2 gap-6">
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center space-x-2">
                    <Plus className="h-5 w-5" />
                    <span>Cadastrar Pagamento</span>
                  </CardTitle>
                  <CardDescription>
                    Registre um novo pagamento no sistema
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <PaymentForm />
                </CardContent>
              </Card>

              <Card>
                <CardHeader>
                  <CardTitle>Lista de Pagamentos</CardTitle>
                  <CardDescription>
                    Todos os pagamentos registrados
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <PaymentList />
                </CardContent>
              </Card>
            </div>
          </TabsContent>
        </Tabs>
      </main>
    </div>
  );
};

export default Index;
