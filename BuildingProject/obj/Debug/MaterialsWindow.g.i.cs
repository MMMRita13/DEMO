﻿#pragma checksum "..\..\MaterialsWindow.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "729E9DFA27BCA11F676D7AD63ECFCC5A5DC08824A41266FE5283B2F6126300F5"
//------------------------------------------------------------------------------
// <auto-generated>
//     Этот код создан программой.
//     Исполняемая версия:4.0.30319.42000
//
//     Изменения в этом файле могут привести к неправильной работе и будут потеряны в случае
//     повторной генерации кода.
// </auto-generated>
//------------------------------------------------------------------------------

using BuildingProject;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace BuildingProject {
    
    
    /// <summary>
    /// MaterialsWindow
    /// </summary>
    public partial class MaterialsWindow : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 23 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnMinimaze;
        
        #line default
        #line hidden
        
        
        #line 42 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnClose;
        
        #line default
        #line hidden
        
        
        #line 65 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox NameMaterialTB;
        
        #line default
        #line hidden
        
        
        #line 68 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox PriceTB;
        
        #line default
        #line hidden
        
        
        #line 71 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox KolvoTB;
        
        #line default
        #line hidden
        
        
        #line 74 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox EdinizTB;
        
        #line default
        #line hidden
        
        
        #line 77 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox CategoryTB;
        
        #line default
        #line hidden
        
        
        #line 80 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox SupervizorTB;
        
        #line default
        #line hidden
        
        
        #line 103 "..\..\MaterialsWindow.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.DataGrid DGMaterial;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/BuildingProject;component/materialswindow.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\MaterialsWindow.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            
            #line 8 "..\..\MaterialsWindow.xaml"
            ((BuildingProject.MaterialsWindow)(target)).MouseLeftButtonDown += new System.Windows.Input.MouseButtonEventHandler(this.Window_MouseLeftButtonDown);
            
            #line default
            #line hidden
            return;
            case 2:
            this.btnMinimaze = ((System.Windows.Controls.Button)(target));
            
            #line 23 "..\..\MaterialsWindow.xaml"
            this.btnMinimaze.Click += new System.Windows.RoutedEventHandler(this.btnMinimize_Click);
            
            #line default
            #line hidden
            return;
            case 3:
            this.btnClose = ((System.Windows.Controls.Button)(target));
            
            #line 42 "..\..\MaterialsWindow.xaml"
            this.btnClose.Click += new System.Windows.RoutedEventHandler(this.btnClose_Click);
            
            #line default
            #line hidden
            return;
            case 4:
            this.NameMaterialTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 65 "..\..\MaterialsWindow.xaml"
            this.NameMaterialTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.NameProjectTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 5:
            this.PriceTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 68 "..\..\MaterialsWindow.xaml"
            this.PriceTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.DataStartTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 6:
            this.KolvoTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 71 "..\..\MaterialsWindow.xaml"
            this.KolvoTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.DataStopTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 7:
            this.EdinizTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 74 "..\..\MaterialsWindow.xaml"
            this.EdinizTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.SupervisorTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 8:
            this.CategoryTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 77 "..\..\MaterialsWindow.xaml"
            this.CategoryTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.MaterialTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 9:
            this.SupervizorTB = ((System.Windows.Controls.TextBox)(target));
            
            #line 80 "..\..\MaterialsWindow.xaml"
            this.SupervizorTB.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.MaterialTB_TextChanged);
            
            #line default
            #line hidden
            return;
            case 10:
            
            #line 82 "..\..\MaterialsWindow.xaml"
            ((System.Windows.Controls.Button)(target)).Click += new System.Windows.RoutedEventHandler(this.AddButton_Click);
            
            #line default
            #line hidden
            return;
            case 11:
            
            #line 102 "..\..\MaterialsWindow.xaml"
            ((System.Windows.Controls.Button)(target)).Click += new System.Windows.RoutedEventHandler(this.DeleteButton_Click);
            
            #line default
            #line hidden
            return;
            case 12:
            this.DGMaterial = ((System.Windows.Controls.DataGrid)(target));
            return;
            }
            this._contentLoaded = true;
        }
    }
}
