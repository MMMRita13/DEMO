using Microsoft.SqlServer.Server;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace BuildingProject
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow  : Window
    {
        ConstructionProjectEntities db;
        MaterialsWindow materials;
        public MainWindow()
        { 
            InitializeComponent();
            materials = new MaterialsWindow();
            db = ConstructionProjectEntities.GetContext();
            DGProject.ItemsSource = db.Project.ToList();
        }

        private void DeleteButton_Click(object sender, RoutedEventArgs e)
        {
            Project project = DGProject.SelectedItem as Project;
            if (project != null)
            {
                db.Project.Remove(project);
                db.SaveChanges();
                DGProject.ItemsSource = db.Project.ToList();
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

        private void AddButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Project project = new Project
                {
                    Nname = NameProjectTB.Text,
                    StartDate = DateTime.Parse(DataStartTB.Text),
                    EndDate = DateTime.Parse(DataStopTB.Text),
                    Supervisor = int.Parse(SupervisorTB.Text),
                    Materials = int.Parse(MaterialTB.Text)
                };
                db.Project.Add(project);
                db.SaveChanges();
                DGProject.ItemsSource = db.Project.ToList();
                NameProjectTB.Clear();
                SupervisorTB.Clear();
                DataStartTB.Clear();
                DataStopTB.Clear();
                MaterialTB.Clear();

                MessageBox.Show($"Данные успешно сохранены!");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Данные не верны: {ex.Message}");
            }
        }

        private void CheckButton_Click(object sender, RoutedEventArgs e)
        {
            materials.Show();
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
