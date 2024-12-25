using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace BuildingProject
{
    /// <summary>
    /// Логика взаимодействия для materialsWorker.xaml
    /// </summary>
    public partial class materialsWorker : Window
    {
        ConstructionProjectEntities db;
        public materialsWorker()
        {
            InitializeComponent();
            db = ConstructionProjectEntities.GetContext();
            DGMaterial.ItemsSource = db.Materials.ToList();
        }

        private void Window_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
        }

        private void btnMinimize_Click(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }
    }
}
