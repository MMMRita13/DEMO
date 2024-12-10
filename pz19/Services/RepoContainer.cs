
using Pizza.Services;
using pz19.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
            _container.RegisterType<IClientRepository, ClientRepository>(
                new ContainerControlledLifetimeManager());
            Container.RegisterType<IRequestRepository, RequestRepository>(new ContainerControlledLifetimeManager());
        }

        public static IUnityContainer Container => _container;
    }
}
