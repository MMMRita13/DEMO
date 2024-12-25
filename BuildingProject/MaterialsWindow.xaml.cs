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
using System.Windows.Media.Media3D;
using System.Windows.Shapes;

namespace BuildingProject
{
    /// <summary>
    /// Логика взаимодействия для MaterialsWindow.xaml
    /// </summary>
    public partial class MaterialsWindow : Window
    {

        ConstructionProjectEntities db;
        public MaterialsWindow()
        {
            InitializeComponent();
            db = ConstructionProjectEntities.GetContext();
            DGMaterial.ItemsSource = db.Materials.ToList();
        }

        private void AddButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Materials material = new Materials
                {
                    Nname = NameMaterialTB.Text,
                    UnitPrice = int.Parse(PriceTB.Text),
                    QuantityStorage= int.Parse(KolvoTB.Text),
                    UnitOfMeasurement = int.Parse(EdinizTB.Text),
                    Сategory = int.Parse(CategoryTB.Text),
                    Providers = int.Parse(SupervizorTB.Text)
                    
                };
                db.Materials.Add(material);
                db.SaveChanges();
                DGMaterial.ItemsSource = db.Materials.ToList();
                NameMaterialTB.Clear();
                PriceTB.Clear();
                KolvoTB.Clear();
                EdinizTB.Clear();
                CategoryTB.Clear();
                SupervizorTB.Clear();

                MessageBox.Show($"Данные успешно сохранены!");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Данные не верны: {ex.Message}");
            }
        }

        private void DeleteButton_Click(object sender, RoutedEventArgs e)
        {
            Materials material = DGMaterial.SelectedItem as Materials;
            if (material != null)
            {
                db.Materials.Remove(material);
                db.SaveChanges();
                DGMaterial.ItemsSource = db.Materials.ToList();
            }
            else
            {
                MessageBox.Show($"Выберите склад для удаления");
            }
        }
                
        private void NameProjectTB_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void DataStartTB_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void DataStopTB_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void SupervisorTB_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void MaterialTB_TextChanged(object sender, TextChangedEventArgs e)
        {

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
