using pz19.ViewModels;
using pz19.Views;
using Unity;
using Unity.Lifetime;

namespace pz19.Services
{
    public static class RepoContainer
    {
        private static IUnityContainer _container;
        static RepoContainer()
        {
            _container = new UnityContainer();
            _container.RegisterType<IClientRepository, ClientRepository>(new ContainerControlledLifetimeManager());
            _container.RegisterType<IRequestRepository, RequestRepository>(new ContainerControlledLifetimeManager());
            _container.RegisterType<RequestViewModel>();
            _container.RegisterType<ClientViewModel>();
        }

        public static IUnityContainer Container => _container;
    }
}
