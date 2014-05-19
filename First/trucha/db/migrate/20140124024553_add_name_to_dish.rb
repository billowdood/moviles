class AddNameToDish < ActiveRecord::Migration
  def change
    add_column :dishes, :name, :string
  end
end
